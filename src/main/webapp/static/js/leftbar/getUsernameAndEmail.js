$(document).ready(function () {
  $.ajax({
    type: 'POST',
    contentType: 'application/json',
    dataType: 'json',
    url: "/mainMenu?action=GetUsername",
    success: function (response) {
      $("#name_left_bar").text(response.login);
      $("#email_left_bar").text(response.email);
    },
    error: function (e) {
      console.log(e);
    }
  });
});
