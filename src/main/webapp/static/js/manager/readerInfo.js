function moreReaderInfo(name, login, email, rowId, penalty, id, status) {
  $('#select_order_block').css("display", "none");
  $('#info_order_block').css("display", "flex");
  $('.reader_username').text(name);
  $('.reader_login').text(login);
  $('.reader_email').text(email);
  $('#order_penalty_info').text("Order Penalty: " + penalty + " ($)");
  $('.current_status_manager').attr('id', 'current_id_' + id);

  setButtonManager(id, status);

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
      $('#reader_count_all_orders').text('All Orders: ' + response.allCount);
      $('#reader_count_active_orders').text(
          'Active Orders: ' + response.activeCount);
      $('#reader_count_penalty').text(
          'Penalty Orders: ' + response.penaltyCount);
      $('#reader_email_readonly').val(email);
      $('#order_tbody tr:not(#' + rowId + ')').each(function () {
        $(this).css("background", "transparent");
      });
      $('#' + rowId).css("background", "#dadada");
    },
    error: function (e) {
      M.toast({html: 'Error Send Request!', classes: 'rounded'});
      console.log(e);
    }
  });
}

function setButtonManager(id, status) {
  if (status === 'NEW') {
    $('.current_status_manager').empty();
    $('.current_status_manager').append(
        '<a href="#"' +
        ' onclick="changeOrderStatus(' + '\'APPROVED\'' + ', ' + id + ', '
        + '\'#order_status_' + id +
        '\', ' + '\'table_row_manager_' + id + '\', ' + '\'LIBRARIAN_ROLE\', '
        + '\'current_id_' + id + '\');"' +
        ' class="waves-effect waves-red btn green">APPROVE</a>'
    );
    $('.current_status_manager').append(
        '<a href="#"' +
        ' onclick="changeOrderStatus(' + '\'CLOSE\'' + ', ' + id + ', '
        + '\'#order_status_' + id +
        '\', ' + '\'table_row_manager_' + id + '\', ' + '\'LIBRARIAN_ROLE\', '
        + '\'current_id_' + id + '\');"' +
        ' class="waves-effect waves-red btn red">CLOSE</a>'
    );
  } else if (status === 'APPROVED') {
    $('.current_status_manager').empty();
    $('.current_status_manager').append(
        '<a href="#" class="btn yellow black-text">ISSUED</a>'
    );
  } else if (status === 'PENALTY') {
    $('.current_status_manager').empty();
    $('.current_status_manager').append(
        '<a href="#" class="btn red">PENALTY</a>'
    );
  } else if (status === 'CLOSE') {
    $('.current_status_manager').empty();
    $('.current_status_manager').append(
        '<a href="/orders/delete?id=' + id + '"><i\n' +
        'class="small material-icons">delete_forever</i></a>'
    );
  } else if (status === 'RETURNS') {
    $('.current_status_manager').empty();
    $('.current_status_manager').append(
        '<a href="#"' +
        ' onclick="changeOrderStatus(' + '\'CLOSE\'' + ', ' + id + ', '
        + '\'#order_status_' + id +
        '\', ' + '\'table_row_manager_' + id + '\', ' + '\'LIBRARIAN_ROLE\', '
        + '\'current_id_' + id + '\');"' +
        ' class="waves-effect waves-red btn blue white-text">APPROVE RETURN</a>'
    );
    $('.current_status_manager').append(
        '<a href="#"' +
        ' onclick="changeOrderStatus(' + '\'PENALTY\'' + ', ' + id + ', '
        + '\'#order_status_' + id +
        '\', ' + '\'table_row_manager_' + id + '\', ' + '\'LIBRARIAN_ROLE\', '
        + '\'current_id_' + id + '\');"' +
        ' class="waves-effect waves-red btn red white-text">SET PENALTY</a>'
    );
  }
}
