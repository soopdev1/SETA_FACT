/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function load_table(table, url) {
    table.DataTable().ajax.url(url).load();
}

function reload_table(table) {
    table.DataTable().ajax.reload(null, false);
}

function clear_table(table) {
    table.DataTable().clear().draw();
}
function jsonToGet(data) {
    return Object.keys(data).map(function (k) {
        return k + '=' + data[k]
    }).join('&');
}


function refresh(table, url, data) {
    $('html, body').animate({scrollTop: $('#offsetresult').offset().top}, 500);
    load_table(table, url + "&" + data);
}

function reload(table) {
    $('html, body').animate({scrollTop: $('#offsetresult').offset().top}, 500);
    reload_table(table);
}