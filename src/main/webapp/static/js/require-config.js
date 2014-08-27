require.config({
    paths: {
        'about-page': '../../lib/about-page',
        backbone: 'find/lib/backbone/backbone-extensions',
        'backbone-base': '../../lib/backbone/backbone',
        bootstrap: '../../lib/bootstrap/js/bootstrap',
        colorbox: '../../lib/colorbox/js/jquery.colorbox',
        'config-wizard': '../../lib/config-wizard',
        d3: '../../lib/d3/d3.v3',
        d3patch: '../../lib/d3/d3.patch',
        datatables: '../../lib/datatables/js/datatables.bootstrap',
        datatables_base: '../../lib/datatables/js/jquery.dataTables',
        'datatables-plugins': '../../lib/datatables/js',
        flot: '../../lib/flot/jquery.flot',
        flotAxisLabels: '../../lib/flot/jquery.flot.axislabels',
        flotNavigate: '../../lib/flot/jquery.flot.navigate',
        flotPie: '../../lib/flot/jquery.flot.pie',
        flotStack: '../../lib/flot/jquery.flot.stack',
        fuelux: '../../lib/fuelux',
        i18n: '../../lib/require/i18n',
        jmousewheel: '../../lib/jquery-mousewheel/js/jquery.mousewheel',
        jquery: '../../lib/jquery/jquery',
        jqueryuiDatePicker: '$appNameInternallib/jquery/jquery.ui.datepicker.i18n',
        'jqueryui-datepicker-base': '../../lib/jquery-ui/jquery.ui.datepicker',
        jqueryTimePicker: 'find/lib/jquery/jquery.ui.timepicker.i18n',
        'jqueryui-timepicker-base': '../../lib/timepicker/js/jquery-ui-timepicker-addon',
        jqueryTimePickerSlider: '../../lib/timepicker/js/jquery-ui-sliderAccess',
        jqueryTree: '../../lib/jqtree/js/tree.jquery',
        jqueryui: 'find/lib/jqueryui',
        'jqueryui-include': '../../lib/jquery-ui',
        'js-utils': '../../lib/javascript-utils',
        json2: '../../lib/json/json2',
        'login-page': '../../lib/login-page',
        moment: '../../lib/moment/moment',
        polyfill: '../../lib/polyfill/polyfill',
        raphael : '../../lib/raphael/raphael',
        scrollNearEnd : '../../lib/scroll-near-end/scroll-near-end',
        settings: '../../lib/settings',
        store: '../../lib/store/store',
        text: '../../lib/require/text',
        underscore: '../../lib/underscore/underscore',
        xeditable: '../../lib/x-editable/x-editable'
    },
    shim: {
        'backbone-base': {
            deps: ['underscore', 'jquery', 'json2'],
            exports: 'Backbone'
        },
        bootstrap: ['jquery'],
        colorbox: ['jquery'],
        d3: ['d3patch', 'polyfill'],
        flot: ['jquery'],
        flotAxisLabels: ['flot'],
        flotNavigate: ['flot', 'jmousewheel'],
        flotPie: ['flot'],
        flotStack: ['flot'],
        jmousewheel: ['jquery'],
        'jqueryui-timepicker-base': ['jqueryui-datepicker-base', 'jqueryui-include/jquery.ui.slider', 'jqueryTimePickerSlider', 'xeditable'],
        jqueryTimePickerSlider: ['jqueryui-include/jquery.ui.slider'],
        jqueryTree: ['jquery'],
        'jqueryui-include/jquery.ui.core': ['jquery'],
        // xeditable needs to load before datepicker so we can make sure we override it
        'jqueryui-datepicker-base': ['jquery', 'jqueryui-include/jquery.ui.core', 'xeditable'],
        'jqueryui-include/jquery.ui.mouse': ['jquery', 'jqueryui-include/jquery.ui.core', 'jqueryui-include/jquery.ui.widget'],
        'jqueryui-include/jquery.ui.position': ['jquery'],
        'jqueryui-include/jquery.ui.slider': ['jquery', 'jqueryui-include/jquery.ui.core', 'jqueryui-include/jquery.ui.widget', 'jqueryui-include/jquery.ui.mouse'],
        'jqueryui-include/jquery.ui.sortable': ['jquery', 'jqueryui-include/jquery.ui.core', 'jqueryui-include/jquery.ui.widget', 'jqueryui-include/jquery.ui.mouse'],
        'jqueryui-include/jquery.ui.tabs': ['jquery', 'jqueryui-include/jquery.ui.core', 'jqueryui-include/jquery.ui.widget'],
        'jqueryui-include/jquery.ui.widget': ['jquery'],
        'jqueryui-include/jquery.effects.core': ['jquery'],
        scrollNearEnd: ['jquery'],
        underscore: {
            exports: '_'
        },
        xeditable: ['jquery', 'bootstrap']
    }
});