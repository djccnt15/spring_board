document.addEventListener("DOMContentLoaded", function () {
  const editorId = document.getElementById("post-update-content").getAttribute("data-editor-id");
  var simplemde = new SimpleMDE({
    element: document.getElementById("post-update-content"),
    autofocus: true,
    spellChecker: false,
    autosave: {
      enabled: true,
      uniqueId: editorId,
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
