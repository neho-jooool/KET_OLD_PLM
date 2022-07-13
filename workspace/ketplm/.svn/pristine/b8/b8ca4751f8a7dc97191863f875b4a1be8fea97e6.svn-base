$(document).ready(function() {
	    $('.menu').tagdrop({tagPaddingTop: '60px',bgColor: '#B1CCED',bgMoverColor: '#7FB0F0',textColor: '#e0e0e0'});
	    $('.menu').css('width', $(window).width());
	    $('#li1').on('click', function(){
	    	parent.movePage('mainList');
	    });
	    $('#li2').on('click', function(){
	    	parent.movePage('auth');
	    });
	    $('#li3').on('click', function(){
	    	parent.movePage('standard');
	    });
	}
);

(function($){
    $.fn.extend({
        tagdrop: function(options) {
            var defaults = {
                tagPaddingTop: '90px',
                tagDefaultPaddingTop: '30px',
                bgColor: '#B1CCED',
                bgMoverColor: '#7FB0F0',
				textColor: '#e0e0e0',
				textDefaultColor: '#fff'
            };
            var options = $.extend(defaults, options);

            return this.each(function() {
                var obj = $(this);
                var li_items = $("li", obj);
                $("li", obj).css('background-color', options.bgColor);
                
                li_items.mouseover(function(){
                    $(this).animate({paddingTop: options.tagPaddingTop}, 300);
                    $(this).css('background-color', options.bgMoverColor);
                    $(this).css('color', options.textColor);
                }).mouseout(function() {
                    $(this).animate({paddingTop: options.tagDefaultPaddingTop}, 500);
                    $("li",$(this).parent()).css('background-color', options.bgColor);
                    $("li",$(this).parent()).css('color', options.textDefaultColor);
                });
            });
        }
    });
})(jQuery);