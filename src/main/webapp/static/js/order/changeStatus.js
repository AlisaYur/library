function changeOrderStatus(newStatus, orderId, idElement, tableRowId, userRole,
    managerInfoReaderId) {
  let data = {};
  data["newStatus"] = newStatus;
  data["orderId"] = orderId;
  let requestData = JSON.stringify(data);
  $.ajax({
    type: 'POST',
    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    data: {requestData},
    dataType: 'json',
    url: "/mainMenu?action=ChangeOrderStatus",
    success: function (response) {
      let successMsq = "Status Changed!";
      let errorMsq = "Status not changed!";

      if (response.msq === errorMsq) {
        M.toast({html: errorMsq, classes: 'rounded'})
      } else if (response.msq === successMsq) {
        $('#return_book').modal('close');
        M.toast({html: successMsq, classes: 'rounded'});
        changeStatus(idElement, response.newStatus);
        if (userRole === 'LIBRARIAN_ROLE') {
          setStatusButtonInManagerCabinet(response.newStatus, orderId,
              managerInfoReaderId, tableRowId);
        } else if (userRole === 'READER_ROLE') {
          setStatusButtonInReaderCabinet(response.newStatus, orderId,
              tableRowId, response.orderId);
        }
      }
    },
    error: function (e) {
      M.toast({html: 'Error Send Request!', classes: 'rounded'});
      console.log(e);
    }
  });
}

function changeStatus(oldStatus, newStatus) {
  $(oldStatus).empty();
  if (newStatus === 'APPROVED') {
    $(oldStatus).append(
        '<div class="dot-yellow tooltipped" data-position="bottom" data-tooltip="Approved"></div>'
    );
  } else if (newStatus === 'PENALTY') {
    $(oldStatus).append(
        '<div class="dot-red tooltipped" data-position="bottom" data-tooltip="Penalty"></div>'
    );
  } else if (newStatus === 'CLOSE') {
    $(oldStatus).append(
        '<div class="dot-grey tooltipped" data-position="bottom" data-tooltip="Closed"></div>'
    );
  } else if (newStatus === 'RETURNS') {
    $(oldStatus).append(
        '<div class="dot-blue tooltipped" data-position="bottom" data-tooltip="Returns"></div>'
    );
  }
}

function setStatusButtonInManagerCabinet(newStatus, orderId,
    managerInfoReaderId, tableRowId) {
  let currentTd = $('#' + managerInfoReaderId);
  if (newStatus === 'APPROVED') {
    currentTd.empty();
    currentTd.append(
        '<a href="#" class="btn yellow black-text">ISSUED</a>'
    );
    let changeStatusInfo = $('#' + tableRowId).attr("onclick");
    let updateOnClick = changeStatusInfo.replace('NEW', 'APPROVED');
    $('#' + tableRowId).attr("onclick", updateOnClick);
  } else if (newStatus === 'CLOSE') {
    $('#' + tableRowId).empty();
  } else if (newStatus === 'PENALTY') {
    currentTd.empty();
    currentTd.append(
        '<a href="#" class="btn red white-text">PENALTY</a>'
    );
    let changeStatusInfo = $('#' + tableRowId).attr("onclick");
    let updateOnClick = changeStatusInfo.replace('RETURNS', 'PENALTY');
    $('#' + tableRowId).attr("onclick", updateOnClick);
  }
}

function setStatusButtonInReaderCabinet(newStatus, orderId, tableRowId,
    penaltySize) {
  let currentTd = $('#' + tableRowId + ' .current_status_reader');
  let currentTdPenalty = $('#' + tableRowId + ' .current_penalty');
  if (newStatus === 'RETURNS') {
    currentTd.empty();
    currentTd.append(
        '<a href="#" class="btn yellow black-text">Check Librarian</a>'
    );
  } else if (newStatus === 'CLOSE') {
    $('#' + tableRowId).remove();
  } else if (newStatus === 'PENALTY') {
    currentTd.empty();
    currentTd.append(
        '<a href="#" onclick="createPayBtn(' + orderId + ', ' + "\x27"
        + 'pay_btn_' + orderId + "\x27" + ')"' +
        ' class="waves-effect waves-red btn red">Pay a fine</a>'
    );
    currentTdPenalty.html(penaltySize + '($)');
  }
}
