let isValidated = false;

$(document).ready(function () {
  $("#submitGenre").click(function () {
    event.preventDefault();
    checkCreateGenre();
    if (isValidated === true) {
      chechGenreName();
    }
  });

  $("#updateGenreSubmit").click(function () {
    event.preventDefault();
    checkUpdateGenre();
  });

  function chechGenreName() {
    let data = {};
    data["name"] = $("#genre-name").val();
    let requestData = JSON.stringify(data);
    $.ajax({
      type: 'POST',
      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
      data: {requestData},
      dataType: 'json',
      url: "/mainMenu?action=CheckGenreName",
      success: function (response) {
        if (response.name === 'genre found!') {
          $("#submitGenre").css("background-color", "red");
          M.toast({
            html: 'Genre with this name already exists!',
            classes: 'rounded'
          })
        } else if (response.name === 'invalid value!') {
          $("#submitGenre").css("background-color", "red");
          M.toast({html: 'Incorrect input!', classes: 'rounded'})
        } else {
          $(".submitGenre").submit();
        }
      },
      error: function () {
        $("#submitGenre").css("background-color", "red");
      }
    });
  }

  function checkCreateGenre() {
    let name = document.getElementById('genre-name').value;
    isValidated = false;

    if (name.length === 0) {
      $("#submitGenre").css("background-color", "red");
      M.toast({html: 'Enter genre name!', classes: 'rounded'})
    } else {
      isValidated = true;
    }
  }

  function checkUpdateGenre() {
    let name = document.getElementById('genre-update-name').value;
    isValidated = false;

    if (name.length === 0) {
      $("#updateGenreSubmit").css("background-color", "red");
      M.toast({html: 'Enter genre name!', classes: 'rounded'})
    } else {
      $(".updateGenreSubmit").submit();
    }
  }
});
