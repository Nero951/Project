// 选择器（Selector） button 的意思，选择所有的 button 标签
let buttonElement = document.querySelector("button");

function onclick(ev) {
    // 有用户点击 button，我们发起 ajax
    let xhr = new XMLHttpRequest();
    xhr.open("get", "/api/article-list.json");
    xhr.onreadystatechange = function (ev) {
        if (xhr.readyState == XMLHttpRequest.DONE) {    // ajax 请求完成时
            if (xhr.status == 200) {                    // HTTP 响应 200 的时候
                let articleList = JSON.parse(xhr.responseText);
                console.log(articleList);

                // 把得到的结果，放入我们的 html 中
                let tbody = document.querySelector("tbody");
                for (let i in articleList) {
                    let article = articleList[i];
                    let html = "<tr>" +
                        "<td>" + article["id"] + "</td>" +
                        "<td>" + article["title"] + "</td>" +
                        "<td>" + article["published-at"] + "</td>" +
                        "<td>" + article["author"]["id"] + "</td>" +
                        "<td>" + article["author"]["username"] + "</td>" +
                        "</tr>";
                    console.log(html);
                    tbody.innerHTML += html;
                }
            }
        }
    };
    xhr.send();
}

// 如果在 button 这个标签上，发生了 click（点击）事件，请执行 onclick 函数
// 每次用户点击 button，就允许 onclick 方法
buttonElement.addEventListener("click", onclick);