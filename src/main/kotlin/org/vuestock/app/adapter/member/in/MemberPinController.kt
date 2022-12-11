package org.vuestock.app.adapter.member.`in`

import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.vuestock.app.adapter.member.`in`.dto.MemberAppendPinRequest
import org.vuestock.app.adapter.member.`in`.dto.MemberPinDeleteRequest
import org.vuestock.app.adapter.member.`in`.dto.MemberStockPinResponse
import org.vuestock.app.application.authentication.port.`in`.JwtTokenDecryptPort
import org.vuestock.app.application.member.port.`in`.MemberAppendPinPort
import org.vuestock.app.application.member.port.`in`.MemberDeletePinPort
import org.vuestock.app.application.member.port.`in`.MemberGetPinsPort
import org.vuestock.app.application.stock.port.`in`.StockCurrentPricePort
import org.vuestock.app.infrastructure.common.CommonResponse
import org.vuestock.app.infrastructure.logger.logger

// TODO: @CookieValue 의 경우 나중 JWT Filter 가 적용됐을 때 @AuthenticationPrincipal 어노테이션을 이용하여 Resolve 한다.
@RestController
@RequestMapping("/api/v1/member/pin")
class MemberPinController(
    val memberAppendPinPort: MemberAppendPinPort,
    val memberDeletePinPort: MemberDeletePinPort,
    val memberGetPinsPort: MemberGetPinsPort,
    val currentStockPort: StockCurrentPricePort,
    val jwtTokenDecryptPort: JwtTokenDecryptPort<String>
) {
    val log = logger<MemberPinController>()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addNewPinning(@RequestBody memberAppendPinRequest: MemberAppendPinRequest,
                      @CookieValue(value = "authenticationToken", defaultValue = "token") jwt: String) {
        try {
            val email = jwtTokenDecryptPort.decryptToken(jwt)
            memberAppendPinPort.appendPin(email, memberAppendPinRequest.stockCode)
        } catch (e: JWTVerificationException) {
            e.printStackTrace()
            log.warn("token's expired")
        }
    }

    @DeleteMapping
    fun deletePin(@RequestBody memberPinDeleteRequest: MemberPinDeleteRequest,
                  @CookieValue(value = "authenticationToken", defaultValue = "token") jwt: String) {
        try {
            val email = jwtTokenDecryptPort.decryptToken(jwt)
            memberDeletePinPort.deleteByStockCode(email, memberPinDeleteRequest.stockCode)
        } catch (e: JWTVerificationException) {
            log.warn("token's expired")
        }
    }

    @GetMapping
    fun getPins(@CookieValue(value = "authenticationToken", defaultValue = "token") jwt: String)
        : CommonResponse<List<MemberStockPinResponse>> {
        try {
            val email = jwtTokenDecryptPort.decryptToken(jwt)
            val stockPins = memberGetPinsPort.getPins(email)
            if (stockPins.isEmpty()) {
                return CommonResponse(
                    message = "관심 종목 조회에 실패하였습니다.",
                    code = "",
                    data = emptyList()
                )
            }
            return CommonResponse(
                message = "성공적으로 관심 종목을 조회하였습니다.",
                code = "",
                data = stockPins.map {
                    val stockPrice = currentStockPort.getCurrentPrice(it.stockCode, false)
                    MemberStockPinResponse(it.stockCode, it.stockName, stockPrice)
                }
            )
        } catch (e: JWTVerificationException) {
            log.warn("token's expired")
        }
        return CommonResponse(
            message = "관심 종목 조회에 실패하였습니다.",
            code = "",
            data = emptyList()
        )
    }
}