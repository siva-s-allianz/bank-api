
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create Bank Account</title>
</head>
<body>

    <h1>Create Bank Account</h1>

    <form:form action="/accounts/create"
               method="post"
               modelAttribute="bankAccountForm">

        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}">

        <label>Customer Name:</label>
        <form:input path="customerName"/>
        <form:errors path="customerName" cssClass="error"/>
        <br><br>

        <label>Account Number:</label>
        <form:input path="accountNumber" type="number"/>
        <form:errors path="accountNumber" cssClass="error"/>
        <br><br>

        <label>Initial Balance:</label>
        <form:input path="balance" type="number"/>
        <form:errors path="balance" cssClass="error"/>
        <br><br>

        <button type="submit">Create Account</button>

    </form:form>

</body>
</html>
