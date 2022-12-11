package org.vuestock.app.application.member.exceptions

class MemberPinAppendException(
    stockCode: String,
    memberEmail: String,
    message: String
) : MemberPinException(stockCode, memberEmail, message)