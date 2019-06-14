function createPayBtn(orderId, idBtn) {
  let data = {};
  data["orderId"] = orderId;
  let requestData = JSON.stringify(data);
  $.ajax({
    type: 'POST',
    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    data: {requestData},
    dataType: 'json',
    url: "/mainMenu?action=Pay",
    success: function (response) {
      let errorMsq = "Create Error!";
      let successMsq = "Success!";

      if (response.msq === successMsq) {
        $('#' + idBtn).empty();
        $('#' + idBtn).append(response.btn);
        M.toast({html: 'Btn for pay created!', classes: 'rounded'})
      } else if (response.msq === errorMsq) {
        M.toast({html: errorMsq, classes: 'rounded'})
      }
    },
    error: function (e) {
      M.toast({html: 'Error Send Request!', classes: 'rounded'});
      console.log(e);
    }
  });
}
