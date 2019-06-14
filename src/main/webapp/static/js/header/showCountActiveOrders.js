function countOrders(login) {
  let data = {};
  data["login"] = login;
  let requestData = JSON.stringify(data);
  $.ajax({
    type: 'POST',
    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    data: {requestData},
    dataType: 'json',
    url: "/mainMenu?action=GetCountAllOrders",
    success: function (response) {
      let activeCount = response.activeCount + response.penaltyCount;
      if (activeCount > 0) {
        $('#basket_count_order').text(activeCount);
      } else {
        $('#basket_count_order').remove();
      }
    },
    error: function (e) {
      console.log(e);
    }
  });
}
