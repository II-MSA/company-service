package org.iimsa.company_service.domain.service;

// 주소를 위도, 경도로 변환
public interface AddressResolver {
    Coordinates resolve(String address);
}
