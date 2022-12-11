package org.vuestock.app.application.member.exceptions.handlers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.vuestock.app.application.member.exceptions.MemberPinException
import org.vuestock.app.application.member.exceptions.MemberStockPinNotFoundException
import org.vuestock.app.infrastructure.common.CommonResponse

@RestControllerAdvice
class MemberPinExceptionHandlers {

    @ExceptionHandler(MemberPinException::class)
    @ResponseStatus(HttpStatus.OK)
    fun memberPinAppendExceptionHandler(exception: MemberPinException)
        : CommonResponse<String> {
        return CommonResponse(exception.message, "", exception.stockCode)
    }

    @ExceptionHandler(MemberStockPinNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun memberPinNotFoundExceptionHandler(exception: MemberStockPinNotFoundException): CommonResponse<String> {
        return CommonResponse(exception.message, "", exception.stockCode)
    }
}