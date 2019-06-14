function switchManagerState(id) {
  let readerSwitcher = document.getElementById("manager-active-" + id).checked;
  let data = {};
  data["id"] = id;
  data["active"] = readerSwitcher;
  let requestData = JSON.stringify(data);
  $.ajax({
    type: 'POST',
    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    data: {requestData},
    dataType: 'json',
    url: "/mainMenu?action=ChangeManagerState&id=" + id + "&active="
        + readerSwitcher
  });
}
