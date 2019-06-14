$(document).ready(function () {
  $(".input_search").focusin(function () {
    $(".search_icon").css("visibility", "hidden");
  });

  $(".input_search").blur(function () {
    if ($(".input_search").val().length > 0) {
      $(".search_icon").css("visibility", "hidden");
    } else {
      $(".search_icon").css("visibility", "visible");
    }
  })
});
