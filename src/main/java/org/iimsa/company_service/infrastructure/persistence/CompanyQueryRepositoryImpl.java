package org.iimsa.company_service.infrastructure.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.model.Company;
import org.iimsa.company_service.domain.model.CompanyId;
import org.iimsa.company_service.domain.query.CompanyQueryDto.Search;
import org.iimsa.company_service.domain.repository.CompanyQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.iimsa.company_service.domain.model.QCompany.company;

@Repository
@RequiredArgsConstructor
public class CompanyQueryRepositoryImpl implements CompanyQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Company> findById(CompanyId companyId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(company)
                        .where(
                                company.id.eq(companyId.id()),
                                company.deletedAt.isNull()
                        )
                        .fetchOne()
        );
    }

    @Override
    public Page<Company> findAllByHubId(UUID hubId, Pageable pageable) {
        return getPage(null, pageable, company.associate.hub.id.eq(hubId));
    }

    @Override
    public Page<Company> findAllByCompanyManagerId(UUID companyManagerId, Pageable pageable) {
        return getPage(null, pageable, company.associate.companyManager.companyManagerId.eq(companyManagerId));
    }

    @Override
    public Page<Company> findAll(Search search, Pageable pageable) {
        return getPage(search, pageable, null);
    }

    private Page<Company> getPage(Search search, Pageable pageable, BooleanExpression baseCondition) {

        BooleanBuilder builder = createSearchCondition(search);
        if (baseCondition != null) {
            builder.and(baseCondition);
        }

        builder.and(company.deletedAt.isNull());

        List<Company> content = queryFactory
                .selectFrom(company)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable).toArray(new OrderSpecifier[0]))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(company.count())
                .from(company)
                .where(builder);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private List<OrderSpecifier<?>> getOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        // 1. 클라이언트가 정렬 조건을 안 보냈을 때 (고정 정렬 로직 유지)
        if (pageable.getSort().isUnsorted()) {
            orders.add(company.createdAt.desc());
            orders.add(company.modifiedAt.desc());
            return orders;
        }

        // 2. 클라이언트가 정렬 조건을 보냈을 때 (동적 정렬 적용)
        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            PathBuilder<Company> entityPath = new PathBuilder<>(Company.class, "company");

            // 경고를 방지하기 위해 unchecked type cast 처리
            @SuppressWarnings({"unchecked", "rawtypes"})
            OrderSpecifier<?> orderSpecifier = new OrderSpecifier(direction, entityPath.get(order.getProperty()));

            orders.add(orderSpecifier);
        }

        return orders;
    }

    private BooleanBuilder createSearchCondition(Search search) {
        BooleanBuilder builder = new BooleanBuilder();

        if (search == null) return builder;

        if (search.getCompanyIds() != null && !search.getCompanyIds().isEmpty()) {
            List<UUID> uuidList = search.getCompanyIds().stream()
                    .map(CompanyId::id)
                    .toList();
            builder.and(company.id.in(uuidList));
        }

        if (search.getHubsIds() != null && !search.getHubsIds().isEmpty()) {
            builder.and(company.associate.hub.id.in(search.getHubsIds()));
        }

        if (search.getCompanyManagersIds() != null && !search.getCompanyManagersIds().isEmpty()) {
            builder.and(company.associate.companyManager.companyManagerId.in(search.getCompanyManagersIds()));
        }

        if (StringUtils.hasText(search.getCompanyName())) {
            builder.and(company.companyName.containsIgnoreCase(search.getCompanyName()));
        }

        if (StringUtils.hasText(search.getHubName())) {
            builder.and(company.associate.hub.name.containsIgnoreCase(search.getHubName()));
        }

        if (StringUtils.hasText(search.getCompanyManagerName())) {
            builder.and(company.associate.companyManager.companyManagerName.containsIgnoreCase(search.getCompanyManagerName()));
        }

        return builder;
    }
}
