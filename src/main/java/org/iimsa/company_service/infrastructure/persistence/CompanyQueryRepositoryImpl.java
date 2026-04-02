package org.iimsa.company_service.infrastructure.persistence;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.model.Company;
import org.iimsa.company_service.domain.query.CompanyQueryDto.Search;
import org.iimsa.company_service.domain.repository.CompanyId;
import org.iimsa.company_service.domain.repository.CompanyQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
                .orderBy(
                        company.createdAt.desc(),
                        company.modifiedAt.desc()
                )
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(company.count())
                .from(company)
                .where(builder);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
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