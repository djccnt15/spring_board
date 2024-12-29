export const CaseType = Object.freeze({
  DEFAULT: "default",
  CREATE: "create",
  UPDATE: "update",
  DELETE: "delete",
});

function handleError(errors) {
  console.error("Error occurred:", errors);
  alert("오류가 발생했습니다:\n" + (errors.message || "알 수 없는 오류"));
}

function createSuccessHandler(form) {
  alert("성공적으로 생성되었습니다.");
  form.reset();
  window.location.reload();
}

function updateSuccessHandler(form) {
  alert("성공적으로 수정되었습니다.");
  form.reset();
  window.location.reload();
}

function deleteSuccessHandler(form) {
  alert("성공적으로 삭제되었습니다.");
  form.reset();
  window.location.reload();
}

function successHandlerFactory(caseType) {
  if (caseType === CaseType.CREATE) {
    return createSuccessHandler;
  } else if (caseType === CaseType.UPDATE) {
    return updateSuccessHandler;
  } else if (caseType === CaseType.DEFAULT) {
    return deleteSuccessHandler;
  }
}

async function submitForm(url, data, method) {
  const response = await fetch(url, {
    method: method,
    body: data,
  });
  if (!response.ok) {
    return response.json().then((errors) => {
      throw errors;
    });
  }
  return await response.text();
}

export function handleFormSubmit(event) {
  event.preventDefault(); // prevent the default form submission behavior

  const form = event.target; // get the form element that triggered the event
  const formData = new FormData(form); // gather form data
  const actionUrl = form.action; // get the URL from the form's action attribute
  const method = form.method; // get the method from the form's method attribute
  const caseType = form.dataset.caseType || CaseType.DEFAULT; // get data-case-type from the form attribute
  const successHandler = successHandlerFactory(caseType);

  // send request
  submitForm(actionUrl, formData, method)
    .then(() => {
      successHandler(form);
    })
    .catch(handleError);
}
