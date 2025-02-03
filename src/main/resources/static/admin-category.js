document.addEventListener("DOMContentLoaded", function () {
  // attach event listeners to forms
  const formClasses = ["create", "delete", "pin-category"];
  formClasses.forEach(className => {
    const forms = document.getElementsByClassName(className);
    Array.from(forms).forEach(form => {
      form.addEventListener("submit", handleFormSubmit);

      // find submit buttons inside the form and prevent event propagation
      const submitButton = form.querySelector('button[type="submit"], input[type="submit"]');
      if (submitButton) {
        submitButton.addEventListener("click", function (event) {
          event.stopPropagation();
        });
      }
    });
  });
});

async function handleFormSubmit(event) {
  event.preventDefault();

  const form = event.target;
  const method = form.querySelector('input[name="_method"]')?.value || form.method;

  switch (method.toUpperCase()) {
    case "POST":
      if (!confirm("생성하시겠습니까?")) return;
      break;
    case "PUT":
    case "PATCH":
      break;
    case "DELETE":
      if (!confirm("삭제하시겠습니까?")) return;
      break;
    default:
      console.warn(`Unsupported method: ${method}`);
  }
  const formData = new FormData(form);
  try {
    const response = await fetch(form.action, {
      method: method,
      body: formData,
    })
    if (!response.ok) {
      const data = await response.json();
      throw new Error(`${data.message}`);
    }
    window.location.reload();
  } catch (error) {
    alert("오류가 발생했습니다:\n" + (error.message || "알 수 없는 오류"));
  }
}
