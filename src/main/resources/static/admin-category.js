document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector("#mainCategoryCreateModal form");

  if (!form) {
    console.error("Form not found in #mainCategoryCreateModal.");
    return;
  }

  form.addEventListener("submit", handleFormSubmit);
});

function handleFormSubmit(event) {
  event.preventDefault();
  const form = event.target;
  const formData = new FormData(form);
  const actionUrl = "/admin/category/main";

  submitForm(actionUrl, formData)
    .then(() => {
      handleSuccess(form);
    })
    .catch(handleError);
}

function submitForm(url, data) {
  return fetch(url, {
    method: "POST",
    body: data,
  }).then((response) => {
    if (!response.ok) {
      return response.json().then((errors) => {
        throw errors;
      });
    }
    return response.text(); // Success response
  });
}

function handleSuccess(form) {
  alert("게시판이 성공적으로 생성되었습니다.");
  form.reset();
  const modal = bootstrap.Modal.getInstance(document.getElementById("mainCategoryCreateModal"));
  if (modal) modal.hide();
  window.location.reload();
}

function handleError(errors) {
  console.error("Error occurred:", errors);
  alert("오류가 발생했습니다:\n" + (errors.message || "알 수 없는 오류"));
}
