
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AJAX Demo</title>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>

    <h1>Spring Boot + jQuery AJAX</h1>

    <button id="loadMessage">Get Message</button>

    <h2 id="result"></h2>

    <script>
        $(document).ready(function () {

            $("#loadMessage").click(function () {

                $.ajax({
                    url: "/api/hello",
                    type: "GET",

                    success: function (response) {
                        $("#result").text(response);
                    },

                    error: function (xhr) {
                        $("#result").text(
                            "Error: " + xhr.status
                        );
                    }
                });

            });

        });
    </script>

</body>
</html>
