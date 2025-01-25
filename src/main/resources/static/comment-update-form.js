document.addEventListener("DOMContentLoaded", function () {
  var simplemde = new SimpleMDE({
    element: document.getElementById("content"),
    autofocus: true,
    spellChecker: false,
    autosave: {
      enabled: true,
      uniqueId: "comment-update-editor",
      delay: 1000,
    },
    toolbar: [
      "bold", "italic", "strikethrough", "heading-smaller", "heading-bigger", "|",
      "code", "quote", "unordered-list", "ordered-list", "|",
      "link", "image", "table", "horizontal-rule", "|",
      "preview", "side-by-side", "fullscreen", "|",
      "guide"
    ]
  });
});
