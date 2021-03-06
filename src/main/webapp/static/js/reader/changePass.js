function changePass() {
  let data = {};
  let oldPass = $("#old_pass_modal_edit").val();
  let newPass = $("#pass_modal_edit").val();
  data["password"] = newPass;
  data["oldPassword"] = oldPass;
  let requestData = JSON.stringify(data);
  $.ajax({
    type: 'POST',
    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    data: {requestData},
    dataType: 'json',
    url: "/mainMenu?action=ChangePassReader",
    success: function (response) {
      let errorMsq = "Error input!";
      let successMsq = "Success!";

      if (response.msq === errorMsq) {
        M.toast({html: 'Incorrect Old or New Password!', classes: 'rounded'})
      } else if (response.msq === successMsq) {
        $('#change_pass').modal('close');
      }
    },
    error: function (e) {
      M.toast({html: 'Error Send Request!', classes: 'rounded'});
      console.log(e);
    }
  });
}
