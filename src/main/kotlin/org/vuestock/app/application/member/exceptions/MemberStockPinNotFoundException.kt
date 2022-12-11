package org.vuestock.app.application.member.exceptions

class MemberStockPinNotFoundException(
    stockCode: String,
    memberEmail: String,
    message: String
): MemberPinException(stockCode, memberEmail, message)