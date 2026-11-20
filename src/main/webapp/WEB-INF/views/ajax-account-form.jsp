
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AJAX Account Form</title>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>

    <h1>Create Account Using AJAX</h1>

    <form id="accountForm">

        <label>Customer Name:</label>
        <input type="text" id="customerName" required>
        <br><br>

        <label>Account Number:</label>
        <input type="number" id="accountNumber" required>
        <br><br>

        <label>Initial Balance:</label>
        <input type="number" id="balance" min="0" required>
        <br><br>

        <button type="submit">Create Account</button>

    </form>

    <h3 id="result"></h3>

    <script>
        $(document).ready(function () {
            alert("jQuery is working!");

            $("#accountForm").submit(function (event) {
                alert("Form submit event detected!");
                event.preventDefault();

                const accountData = {
                    customerName: $("#customerName").val(),
                    accountNumber: Number($("#accountNumber").val()),
                    balance: Number($("#balance").val())
                };

                $.ajax({
                    url: "/api/ajax-account",
                    type: "POST",
                    contentType: "application/json",

                    // CSRF protection
                    headers: {
                        [ $("meta[name='_csrf_header']").attr("content") ]:
                            $("meta[name='_csrf']").attr("content")
                    },

                    data: JSON.stringify(accountData),

                    success: function(response) {
                        $("#result").text(response.message);
                    },

                    error: function(xhr) {
                        if (xhr.responseJSON && xhr.responseJSON.message) {
                            $("#result").text(xhr.responseJSON.message);
                        } else {
                            $("#result").text("Something went wrong. Please try again.");
                        }
                    }
                });

            });

        });
    </script>

</body>
</html>
