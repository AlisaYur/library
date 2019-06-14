function switchReaderState(id) {
  let readerSwitcher = document.getElementById("reader-active-" + id).checked;
  let data = {};
  data["id"] = id;
  data["active"] = readerSwitcher;
  let requestData = JSON.stringify(data);
  $.ajax({
    type: 'POST',
    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
    data: {requestData},
    dataType: 'json',
    url: "/mainMenu?action=ChangeReaderState&id=" + id + "&active="
        + readerSwitcher
  });
}