const createForm = document.getElementsByClassName("create")
Array.from(createForm).forEach(function(element) {
  element.addEventListener("submit", handleFormSubmit)
});

const deleteForm = document.getElementsByClassName("delete");
Array.from(deleteForm).forEach(function(element) {
  element.addEventListener("submit", handleFormSubmit);
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
      if (!confirm("수정하시겠습니까?")) return;
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
