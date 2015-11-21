$(function(){
  $('.adjust').click(function() {
    var parentDiv = $(this).parent().parent();
    $('.jobdescription', parentDiv).toggleClass('justapeek');

    return false;
  });
})
