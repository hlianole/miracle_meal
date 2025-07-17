package cz.cvut.fit.hlianole.miracle_meal_app.core.domain

class HttpException (
    statusCode: Int,
    body: String
) : Exception() {
    override val message = "Status code: $statusCode, message: $body"
}