$.fn.datepicker.dates['en'] = {
    days: ['Domenica', 'Lunedì', 'Martedì', 'Mercoledì', 'Giovedì', 'Venerdì', 'Sabato'],
    daysShort: ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab'],
    daysMin: ['Do', 'Lu', 'Ma', 'Me', 'Gio', 'Ve', 'Sa'],
    months: ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'],
    monthsShort: ['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Giu', 'Lug', 'Ago', 'Set', 'Ott', 'Nov', 'Dic'],
    prevText: 'Prec',
    nextText: 'Succ',
    currentText: 'Oggi',
    format: "dd/mm/yyyy",
    titleFormat: "MM yyyy",
    weekStart: 1
};
$('.datepicker').datepicker({
    format: 'dd/mm/yyyy',
    autoclose: true,
    orientation: "bottom left",
    todayHighlight: true,
    zIndexOffset: 999
});
///////////
/**
 * This plug-in will provide date sorting for the "dd/mm/YYYY hh:ii:ss" 
 * formatting, which is common in France and other European countries. It can 
 * also be quickly adapted for other formatting as required. Furthermore, this 
 * date sorting plug-in allows for empty values in the column.
 *
 * Please note that this plug-in is **deprecated*. The
 * [datetime](//datatables.net/blog/2014-12-18) plug-in provides enhanced
 * functionality and flexibility.
 *
 *  @name Date (dd/mm/YYYY hh:ii:ss) 
 *  @summary Sort date / time in the format `dd/mm/YYYY hh:ii:ss`
 *  @author [Ronan Guilloux](http://coolforest.net/)
 *  @deprecated
 *
 *  @example
 *    $('#example').dataTable( {
 *       columnDefs: [
 *         { type: 'date-euro', targets: 0 }
 *       ]
 *    } );
 */

 jQuery.extend( jQuery.fn.dataTableExt.oSort, {
    "date-euro-pre": function ( a ) {
        var x;

        if ( $.trim(a) !== '' ) {
            var frDatea = $.trim(a).split(' ');
            var frTimea = (undefined !== frDatea[1]) ? frDatea[1].split(':') : [0,0,0];
            var frDatea2 = frDatea[0].split('/');
            x = (frDatea2[2] + frDatea2[1] + frDatea2[0] + frTimea[0] + frTimea[1] + ((undefined !== frTimea[2]) ? frTimea[2] : 0)) * 1;
        }
        else {
            x = Infinity;
        }

        return x;
    },

    "date-euro-asc": function ( a, b ) {
        return a - b;
    },

    "date-euro-desc": function ( a, b ) {
        return b - a;
    }
} );


/**
 * Similar to the Date (dd/mm/YY) data sorting plug-in, this plug-in offers 
 * additional  flexibility with support for spaces between the values and
 * either . or / notation for the separators.
 *
 * Please note that this plug-in is **deprecated*. The
 * [datetime](//datatables.net/blog/2014-12-18) plug-in provides enhanced
 * functionality and flexibility.
 *
 *  @name Date (dd . mm[ . YYYY]) 
 *  @summary Sort dates in the format `dd/mm/YY[YY]` (with optional spaces)
 *  @author [Robert Sedovšek](http://galjot.si/)
 *  @deprecated
 *
 *  @example
 *    $('#example').dataTable( {
 *       columnDefs: [
 *         { type: 'date-eu', targets: 0 }
 *       ]
 *    } );
 */

jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	"date-eu-pre": function ( date ) {
		date = date.replace(" ", "");
		
		if ( ! date ) {
			return 0;
		}

		var year;
		var eu_date = date.split(/[\.\-\/]/);

		/*year (optional)*/
		if ( eu_date[2] ) {
			year = eu_date[2];
		}
		else {
			year = 0;
		}

		/*month*/
		var month = eu_date[1];
		if ( month.length === 1 ) {
			month = 0+month;
		}

		/*day*/
		var day = eu_date[0];
		if ( day.length === 1 ) {
			day = 0+day;
		}

		return (year + month + day) * 1;
	},

	"date-eu-asc": function ( a, b ) {
		return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	},

	"date-eu-desc": function ( a, b ) {
		return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	}
} );