let isValidated = false;

$(document).ready(function () {
  $("#submitAuthor").click(function () {
    event.preventDefault();
    checkCreateFields();
    if (isValidated === true) {
      checkAuthorLastNameCreate();
    }
  });

  $("#updateAuthorSubmit").click(function () {
    event.preventDefault();
    checkUpdateFields();
  });

  function checkAuthorLastNameCreate() {
    let data = {};
    data["lastName"] = $("#author-create-lname").val();
    let requestData = JSON.stringify(data);
    $.ajax({
      type: 'POST',
      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
      data: {requestData},
      dataType: 'json',
      url: "/mainMenu?action=CheckAuthorLastName",
      success: function (response) {
        if (response.lastName === 'author found!') {
          $("#submitAuthor").css("background-color", "red");
          M.toast({
            html: 'Author with this Last Name already exists!',
            classes: 'rounded'
          })
        } else if (response.lastName === 'invalid value!') {
          $("#submitAuthor").css("background-color", "red");
          M.toast({html: 'Incorrect input!', classes: 'rounded'})
        } else {
          $(".submitAuthor").submit();
        }
      },
      error: function () {
        $("#submitAuthor").css("background-color", "red");
      }
    });
  }

  function checkCreateFields() {
    let firstname = document.getElementById('author-create-fname').value;
    let lastname = document.getElementById('author-create-lname').value;
    isValidated = false;

    if (firstname.length === 0 || lastname.length === 0) {
      $("#submitAuthor").css("background-color", "red");
      M.toast({html: 'All fields are required!', classes: 'rounded'})
    } else {
      isValidated = true;
    }
  }

  function checkUpdateFields() {
    let firstname = document.getElementById('author-update-fname').value;
    let lastname = document.getElementById('author-update-lname').value;
    isValidated = false;

    if (firstname.length === 0 || lastname.length === 0) {
      $("#updateAuthorSubmit").css("background-color", "red");
      M.toast({html: 'All fields are required!', classes: 'rounded'})
    } else {
      isValidated = true;
    }
  }
});
