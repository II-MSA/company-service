package org.iimsa.company_service.infrastructure.messaging.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iimsa.common.messaging.annotation.IdempotentConsumer;
import org.iimsa.common.util.JsonUtil;
import org.iimsa.company_service.application.service.CompanyMasterService;
import org.iimsa.company_service.domain.model.CompanyManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyManagerUpdateListener {

    private final CompanyMasterService companyMasterService;

    @Transactional
    @IdempotentConsumer("companyManager-updated")
    @KafkaListener(topics = "${topics.companyManager.updated}", groupId = "company-group")
    public void onUpdated(Message<String> message, Acknowledgment ack) {
        try {
            CompanyManager companyManager = JsonUtil.fromJson(message.getPayload(), CompanyManager.class);

            if (companyManager == null) {
                throw new IllegalArgumentException("Invalid companyManager update event payload");
            }

            companyMasterService.syncCompanyManagerInfo(companyManager);
            log.info("업체 관리자 업데이트 처리 완료: companyManagerId={}", companyManager.getCompanyManagerId());
            ack.acknowledge();

        } catch (Exception e) {
            log.error("업체 관리자 업데이트 처리 실패:{}", e.getMessage(), e);
            throw e;
        }
    }

    @KafkaListener(topics = "${topics.companyManager.updated}.DLT", groupId = "company-group")
    public void handleDLT(Message<String> message, Acknowledgment ack) {
        log.error("DLT 수신 시작 (처리에 실패한 메시지가 DLT로 인입되었습니다)");

        try {
            CompanyManager companyManager = JsonUtil.fromJson(message.getPayload(), CompanyManager.class);
            log.error("업체 관리자 업데이트 최종 실패: companyManagerId={}", companyManager.getCompanyManagerId());
        } catch (Exception e) {
            log.error("DLT 메시지 변환 실패: {}", e.getMessage());
        } finally {
            ack.acknowledge();
        }
    }
}
