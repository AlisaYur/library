$(document).ready(function () {
  $("#submitButton").click(function () {
    event.preventDefault();
    checkLogin();
  });

  $("#submitRegButton").click(function () {
    event.preventDefault();
    if (checkInputFields()) {
      checkRegInfo();
    }
  });

  function checkLogin() {
    let data = {};
    data["login"] = $("#username").val();
    data["password"] = $("#password").val();
    let requestData = JSON.stringify(data);
    $.ajax({
      type: 'POST',
      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
      data: {requestData},
      dataType: 'json',
      url: "/mainMenu?action=CheckUserLogin",
      success: function (response) {
        if (response.status === 'Error!') {
          $("#submitButton").css("background-color", "red");
          M.toast({html: response.message, classes: 'rounded'})
        } else if (response.status === 'Success!') {
          $(".submitForm").submit();
        }
      },
      error: function (e) {
        console.log(e);
        $("#submitButton").css("background-color", "red");
        M.toast({html: 'Incorrect username or password!', classes: 'rounded'})
      }
    });
  }

  function checkRegInfo() {
    let data = {};
    data["login"] = $("#login").val();
    data["email"] = $("#email").val();
    let requestData = JSON.stringify(data);
    $.ajax({
      type: 'POST',
      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
      data: {requestData},
      dataType: 'json',
      url: "/mainMenu?action=CheckReaderLoginAndEmail",
      success: function (response) {
        if (response.login === 'login or email already in use') {
          $("#submitRegButton").css("background-color", "red");
          M.toast({html: 'Login or email already in use!', classes: 'rounded'})
        } else {
          $(".registrationForm").submit();
        }
      },
      error: function () {
        $("#submitRegButton").css("background-color", "red");
        M.toast({html: 'Incorrect input!', classes: 'rounded'})
      }
    });
  }

  function checkInputFields() {
    if (document.getElementById('name').value < 1) {
      $("#submitRegButton").css("background-color", "red");
      M.toast({html: 'Enter name!', classes: 'rounded'});
      return false;
    } else if (document.getElementById('login').value < 1) {
      $("#submitRegButton").css("background-color", "red");
      M.toast({html: 'Enter login!', classes: 'rounded'})
      return false;
    } else if (document.getElementById('password').value < 1) {
      $("#submitRegButton").css("background-color", "red");
      M.toast({html: 'Enter password!', classes: 'rounded'})
      return false;
    } else if (document.getElementById('email').value < 1) {
      $("#submitRegButton").css("background-color", "red");
      M.toast({html: 'Enter email!', classes: 'rounded'})
      return false;
    }
    return true;
  }
});
