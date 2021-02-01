package com.optum.mobile.template.data.model

data class Config(
    val id: String,
    val demographics: Demographics,
    val agreementStatus: AgreementStatus,
    val features: List<String>?
) {

    data class Demographics(
        val firstName: String,
        val lastName: String
    )

    data class AppMessage(
        val id: Int,
        val text: String,
        val dismissible: Boolean
    )

    data class AgreementStatus(
        val hipaa: Boolean?,
        val privacyPolicy: Boolean?,
        val legal: Boolean?
    )
}