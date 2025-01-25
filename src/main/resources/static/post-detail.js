const deleteForm = document.getElementsByClassName("delete");
Array.from(deleteForm).forEach(function(element) {
  element.addEventListener("submit", handleFormSubmit);
});

async function handleFormSubmit(event) {
  event.preventDefault();
  if (!confirm("삭제하시겠습니까?")) return;
  try {
    const form = event.target;
    const actionUrl = form.action;
    const method = form.querySelector('input[name="_method"]')?.value || form.method;
    const formData = new FormData(form);
    const redirectUrl = await submitForm(actionUrl, formData, method);
    window.location.replace(redirectUrl);
  } catch (error) {
    handleError(error);
  }
}

async function submitForm(url, data, method) {
  const response = await fetch(url, { method, body: data });
  redirectUrl = response.headers.get("redirectUrl")
  if (redirectUrl) {
    return redirectUrl;
  }
  throw await response.json();
}

function handleError(errors) {
  console.error("Error occurred:", errors);
  alert("오류가 발생했습니다:\n" + (errors.message || "알 수 없는 오류"));
}

document.addEventListener("DOMContentLoaded", function () {
  var simplemde = new SimpleMDE({
    element: document.getElementById("text-area"),
    spellChecker: false,
    autosave: {
      enabled: true,
      uniqueId: "comment-editor",
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
