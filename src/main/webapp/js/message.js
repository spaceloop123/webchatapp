/*
 Message
 */
function createMessage(elem) {
    var message = createMessageContainer();
    var li = document.createElement("li");

    if (elem.removed) {
        li = createRemovedMessage(li, elem.author);
        message.appendChild(li);
        return message;
    }

    var author = createAuthor(elem.author);
    var doubleDot = createDoubleDot();
    var editbtn;
    if (User.login === elem.author) {
        editbtn = createEditBtn();
    }
    var text = createText(elem.text);
    var date = createDate(elem.timestamp);

    li.appendChild(author);
    li.appendChild(doubleDot);
    if (User.login === elem.author) {
        li.appendChild(editbtn);
    }
    li.appendChild(text);
    li.appendChild(date);

    if (elem.edited) {
        li.appendChild(createEditedLabel());
    }

    message.appendChild(li);

    return message;
}

function createRemovedMessage(li, author) {
    var label = createDeleteLabel(author);
    li.appendChild(label);
    return li;
}

function createMessageContainer() {
    var div = document.createElement("div");
    div.className = "msg";
    return div;
}

function createElem(divType, className, innerText) {
    var div = document.createElement(divType);
    div.className = className;
    div.innerText = innerText;
    return div;
}

function createAuthor(author) {
    return createElem("div", "author", author);
}

function createDoubleDot() {
    return createElem("span", "double-dot", ":");
}

function createText(value) {
    return createElem("div", "text", value);
}

function createEditedLabel() {
    return createElem("div", "changed", "Edited by author");
}

function createDeleteLabel(author) {
    return createElem("div", "changed", "Deleted by " + author);
}

function createDate(date) {
    return createElem("div", "date", formatDate(new Date(date)));
}

function formatDate(date) {
    var hour = date.getHours();
    var minute = date.getMinutes();
    var amPM = (hour > 11) ? "pm" : "am";
    if (hour > 12) {
        hour -= 12;
    }
    else if (hour === 0) {
        hour = "12";
    }
    if (minute < 10) {
        minute = "0" + minute;
    }
    return hour + ":" + minute + amPM;
}

function createEditBtn() {
    var div = document.createElement("div");
    div.className = "edit-mes";

    var pencil = document.createElement("i");
    pencil.className = "fa fa-pencil";
    var trash = document.createElement("i");
    trash.className = "fa fa-trash";

    div.appendChild(pencil);
    div.appendChild(trash);

    return div;
}