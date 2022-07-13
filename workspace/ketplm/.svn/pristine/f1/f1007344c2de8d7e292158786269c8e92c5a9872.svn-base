$(function () {
    var
        $table = $('.tree-table'),
        rows = $table.find('tr');

    rows.each(function (index, row) {
        var
            $row = $(row),
            level = $row.data('level'),
            id = $row.data('id'),
            $columnName = $row.find('td[data-column="name"]'),
            children = $table.find('tr[data-parent="' + id + '"]');

        if (children.length) {
            var expander = $columnName.prepend('<i class="treegrid-expander icon-arrow-down"></i>');
            //children.hide();

            expander.on('click', function (e) {
                var $target = $(e.target);
                
                window.console.log("################",$target);
                if($target.is("td") || $target.is("span")){
                	$target = $target.find("i");
                }
                
            	if ($target.hasClass('icon-arrow-right')) {
                    $target
                        .removeClass('icon-arrow-right')
                        .addClass('icon-arrow-down');

                    children.show();
                } else {
                    $target
                        .removeClass('icon-arrow-down')
                        .addClass('icon-arrow-right');
                    reverseHide($table, $row);
                }
                
            });
        }else{
        	$columnName.prepend('<span class="treegrid-indent" style="width:8px"></span>');
        }

        $columnName.prepend('' +
            '<span class="treegrid-indent" style="width:' + 15 * level + 'px"></span>' +
            '');
    });

    // Reverse hide all elements
    reverseHide = function (table, element) {
        var
            $element = $(element),
            id = $element.data('id'),
            children = table.find('tr[data-parent="' + id + '"]');

        if (children.length) {
            children.each(function (i, e) {
                reverseHide(table, e);
            });

            $element
                .find('.icon-arrow-down')
                .removeClass('icon-arrow-down')
                .addClass('icon-arrow-right');

            children.hide();
        }
    };
});
