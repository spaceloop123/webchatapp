/*
 Side menu in mobile and tablet version
 */
var layout = document.getElementById('layout'),
    menu = document.getElementById('menu'),
    menuLink = document.getElementById('menuLink');

menuLink.addEventListener("click", function (e) {
    e.preventDefault();

    toggleClass(layout, "active");
    toggleClass(menu, "active");
    toggleClass(menuLink, "active");
});

/*
 FAB logic
 */
var isEdit = false;

var fab = document.getElementById("fab");
var accept = document.getElementById("accept");
var decline = document.getElementById("decline");
var textInputName = document.getElementById("text-input-name");
var myName = document.getElementById("myname-edit");
var pencilIcon = document.getElementById("pencil-icon");

var user = document.getElementById("profile-name");

var delay;

fab.addEventListener("click", function (e) {
    e.preventDefault();

    if (!isEdit) {
        addClass(fab, "active");
        addClass(accept, "active");
        addClass(decline, "active");
        addClass(textInputName, "active");
        addClass(pencilIcon, "active");

        isEdit = true;
        delay = setTimeout(focusDelay, 280);
        myName.value = user.textContent;
    }
});

function focusDelay() {
    document.getElementById("myname-edit").focus();
}

accept.addEventListener("click", function (e) {
    e.preventDefault();

    if (isEdit && !myName.value) {
        return false;
    }

    if (isEdit) {
        finishEdit();

        if (myName.value !== author) {
            sendAlertMessage("\"" + user.textContent
                + "\" is now known as \""
                + myName.value + "\"", "Server", "success");
            author = myName.value;
            renderAuthor(author);
            saveAuthor(author);
        }
    }
});

decline.addEventListener("click", function (e) {
    e.preventDefault();

    if (isEdit) {
        finishEdit();
    }
});

function finishEdit() {
    removeClass(fab, "active");
    removeClass(accept, "active");
    removeClass(decline, "active");
    removeClass(textInputName, "active");
    removeClass(pencilIcon, "active");

    isEdit = false;

    clearTimeout(delay);
}