var msgInputArea = document.getElementById("msg-input-area");
var msgInputBtn = document.getElementById("msg-input-btn");

var isEditMessage = false;
var divForEdit;

msgInputArea.addEventListener('keydown', function (e) {
    if (e.keyCode == 13 && e.ctrlKey) {
        if (this.value === "")
            return false;

        if (!isEditMessage) {
            addMessage(this.value, function () {
                render(Application);
            });
        } else {
            switchBtnIcon();
            isEditMessage = false;

            var index = indexById(Application.messageList, idFromElement(divForEdit));
            var message = Application.messageList[index];
            message.edited = !message.edited;
            message.text = this.value;
            message.timestamp = new Date().getTime();
            message.author = User.author;
            if (divForEdit.firstChild.getElementsByClassName("changed")[0] === undefined) {
                divForEdit.firstChild.appendChild(createEditedLabel());
            }
            divForEdit.getElementsByClassName("text")[0].innerText = message.text;

            ajax('PUT', Application.mainUrl, JSON.stringify(message), function () {
                Application.messageList.splice(index, 1);
                Application.messageList.push(message);
                done();
            });

            removeClass(divForEdit, "active");
        }

        this.value = "";

        return false;
    }
});

msgInputBtn.addEventListener('click', function () {
    if (msgInputArea.value === "")
        return true;

    if (!isEditMessage) {
        addClass(this, "active");

        addMessage(msgInputArea.value, function () {
            render(Application);
        });

        removeClass(this, "active");
    } else {
        switchBtnIcon();
        isEditMessage = false;

        var index = indexById(Application.messageList, idFromElement(divForEdit));
        var message = Application.messageList[index];
        message.edited = !message.edited;
        message.text = msgInputArea.value;
        message.timestamp = new Date().getTime();
        message.author = User.author;
        if (divForEdit.firstChild.getElementsByClassName("changed")[0] === undefined) {
            divForEdit.firstChild.appendChild(createEditedLabel());
        }
        divForEdit.getElementsByClassName("text")[0].innerText = message.text;

        ajax('PUT', Application.mainUrl, JSON.stringify(message), function () {
            Application.messageList.splice(index, 1);
            Application.messageList.push(message);
            done();
        });

        removeClass(divForEdit, "active");
    }

    msgInputArea.value = "";

    return false;
}, true);

/*
 Edit message
 */
function onEditBtnClick(li) {
    console.log(li);
    if (!isEditMessage) {
        switchBtnIcon();

        isEditMessage = true;
        divForEdit = li.parentElement;

        msgInputArea.value = li.getElementsByClassName("text")[0].innerText;
        document.getElementById("msg-input-area").focus();

        addClass(divForEdit, "active");
    } else {
        switchBtnIcon();

        isEditMessage = false;

        removeClass(divForEdit, "active");
    }
}

function switchBtnIcon() {
    if (!isEditMessage) {
        removeClass(document.getElementById("paper-plane"), "fa-paper-plane");
        addClass(document.getElementById("paper-plane"), "fa-pencil");

        removeClass(document.getElementById("msg-input-btn"), "plane");
        addClass(document.getElementById("msg-input-btn"), "pencil");

    } else {
        removeClass(document.getElementById("paper-plane"), "fa-pencil");
        addClass(document.getElementById("paper-plane"), "fa-paper-plane");

        removeClass(document.getElementById("msg-input-btn"), "pencil");
        addClass(document.getElementById("msg-input-btn"), "plane");
    }
}

/*   
 Delete message
 */
var cancelBtn = document.getElementById("cancel");
var deleteBtn = document.getElementById("delete");
var divForDelete;

function onRemoveBtnClick(li) {
    addClass(document.getElementById("overflow-background"), "active");
    addClass(document.getElementById("dialog"), "active");
    document.getElementById("dialog").focus();
    divForDelete = li.parentElement;
}

cancelBtn.addEventListener("click", function (e) {
    removeClass(document.getElementById("overflow-background"), "active");
    removeClass(document.getElementById("dialog"), "active");
});

deleteBtn.addEventListener("click", function (e) {
    var index = indexById(Application.messageList, idFromElement(divForDelete));
    var message = Application.messageList[index];

    var author = divForDelete.firstChild.getElementsByClassName("author")[0].innerText;

    deleteMessage(message.id, function () {
        divForDelete.removeChild(divForDelete.firstChild);

        var li = document.createElement("li");
        li = createRemovedMessage(li, author);
        divForDelete.appendChild(li);
        addClass(divForDelete, "removed");

        render(Application);
    });

    removeClass(document.getElementById("overflow-background"), "active");
    removeClass(document.getElementById("dialog"), "active");
});

function indexById(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id == id) {
            return i;
        }
    }
    return -1;
}

function idFromElement(element) {
    return element.attributes['data-msg-id'].value;
}

function elementById(id) {
    var messages = document.getElementsByClassName("chat-list")[0].childNodes;
    console.log(messages);
    [].forEach.call(messages, function (elem) {
        console.log(elem);
    });
}