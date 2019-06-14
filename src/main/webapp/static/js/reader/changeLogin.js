function changeLogin() {
  let data = {};
  let newLogin = $("#name_modal_edit").val();
  data["login"] = newLogin;
  let requestData = JSON.stringify(data);
  $.ajax({
    type: 'POST',
    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    data: {requestData},
    dataType: 'json',
    url: "/mainMenu?action=ChangeLoginReader",
    success: function (response) {
      let errorMsq = "Error input!";
      let successMsq = "Success!";

      if (response.msq === errorMsq) {
        M.toast({html: 'Incorrect login!', classes: 'rounded'})
      } else if (response.msq === successMsq) {
        $('#change_name').modal('close');
        $("#name_left_bar").html(newLogin);
      }
    },
    error: function (e) {
      M.toast({html: 'Error Send Request!', classes: 'rounded'});
      console.log(e);
    }
  });
}
