var userAgent = window.navigator.userAgent.toLowerCase();
console.log(userAgent);
if (userAgent.indexOf("msie") != -1 || userAgent.indexOf("trident") != -1 || userAgent.indexOf("edge") != -1) {
    document.getElementById("content").innerText = "お使いのブラウザには対応していませんChromeを使用してください。" +
        "";
} else {
    $.ajax({
        type: "GET",
        url: "/markdown/about.md"
    }).done(function (data) {
        document.getElementById("content").innerHTML = marked(data);

        setTableStyles(document.getElementsByTagName("table"));
        setCodeStyles(document.getElementsByTagName("pre"));

        var index = document.getElementById("index");
        createIndex(index);
        setIndexStyle(index);

    }).fail(function () {
        document.getElementById("content").innerText = "データの取得に失敗しました";
    });
}

var setTableStyles = function (ele) {
    Object.keys(ele).forEach(function (i) {
        ele[i].setAttribute("class", "table");
    });
};

var setCodeStyles = function (ele) {
    Object.keys(ele).forEach(function (i) {
        ele[i].setAttribute("class", "prettyprint");
        ele[i].style.padding = "10px";
        ele[i].style.border = "1px solid gray";
        ele[i].style.borderRadius = "5px";
        ele[i].style.boxShadow = "2px 2px 4px";
    });
};

var createIndex = function (parent) {
    var title = document.createElement("h4");
    title.innerText = "INDEX";

    var ol = document.createElement("ol");
    var list = document.getElementsByTagName("h2");

    var idx = 1;
    Object.keys(list).forEach(function (i) {
        var li = document.createElement("li");
        li.innerText = list[i].innerText;
        ol.appendChild(li);

        list[i].innerText = idx++ + ". " + list[i].innerText;
    });
    parent.appendChild(title);
    parent.appendChild(ol);
};

var setIndexStyle = function (ele) {
    ele.style.border = "2px dashed gray";
    ele.style.background = "#ededed";
    ele.style.borderRadius = "5px";
    ele.style.padding = "10px";
};