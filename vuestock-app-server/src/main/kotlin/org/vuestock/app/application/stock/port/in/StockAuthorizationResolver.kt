package org.vuestock.app.application.stock.port.`in`

import org.vuestock.app.application.stock.port.`in`.dto.StockAuthorization

interface StockAuthorizationResolver {

    /**
     * 현재 저장된 KIS API 인증 정보를 얻어옵니다.
     * appKey, appSecret은 설정 정보에서 가져오고 액세스 토큰은 캐시에서 가져와 데이터 오브젝트를 반환합니다.
     * */
    fun getAuthorizationInfo(): StockAuthorization?
}