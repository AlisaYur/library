function changeEmail() {
  let data = {};
  let newEmail = $("#email_modal_edit").val();
  data["email"] = newEmail;
  let requestData = JSON.stringify(data);
  $.ajax({
    type: 'POST',
    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    data: {requestData},
    dataType: 'json',
    url: "/mainMenu?action=ChangeEmailReader",
    success: function (response) {
      let errorMsq = "Error input!";
      let successMsq = "Success!";

      if (response.msq === errorMsq) {
        M.toast({html: 'Incorrect email!', classes: 'rounded'})
      } else if (response.msq === successMsq) {
        $('#change_email').modal('close');
        $("#email_left_bar").html(newEmail);
      }
    },
    error: function (e) {
      M.toast({html: 'Error Send Request!', classes: 'rounded'});
      console.log(e);
    }
  });
}
