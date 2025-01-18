export const CaseType = Object.freeze({
  POST: "POST",
  PUT: "PUT",
  PATCH: "PATCH",
  DELETE: "DELETE",
});

function handleError(errors) {
  console.error("Error occurred:", errors);
  alert("오류가 발생했습니다:\n" + (errors.message || "알 수 없는 오류"));
}

function handlingCreateSuccess(formData) {
  const name = formData.get("name");
  alert(`${name}이/가 성공적으로 생성되었습니다.`);
  window.location.reload();
}

function handlingUpdateSuccess(formData) {
  const name = formData.get("name");
  alert(`${name}이/가 성공적으로 수정되었습니다.`);
  window.location.reload();
}

function handlingDeleteSuccess(formData) {
  const name = formData.get("name");
  alert(`${name}이/가 성공적으로 삭제되었습니다.`);
  window.location.reload();
}

function successHandlerFactory(caseType) {
  switch (caseType) {
    case CaseType.POST:
      return handlingCreateSuccess;
    case CaseType.PUT:
      return handlingUpdateSuccess;
    case CaseType.PATCH:
      return handlingUpdateSuccess;
    case CaseType.DELETE:
      return handlingDeleteSuccess;
    default:
      throw new Error(`Unhandled case type: ${caseType}`);
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

function handlingConfirmCreate(formData) {
  const name = formData.get("name");
  const confirmation = confirm(`${name}을/를 생성하시겠습니까?`);
  if (!confirmation) {
    return false;
  }
  return true;
}

function handlingConfirmUpdate(formData) {
  const confirmation = confirm(`수정하시겠습니까?`);
  if (!confirmation) {
    return false;
  }
  return true;
}

function handlingConfirmDelete(formData) {
  const name = formData.get("name");
  const confirmMessage = name ? `${name}을/를 삭제하시겠습니까?` : "삭제하시겠습니까?";

  const confirmation = confirm(confirmMessage);
  if (!confirmation) {
    return false;
  }
  return true;
}

function confirmHandlerFactory(caseType) {
  switch (caseType) {
    case CaseType.POST:
      return handlingConfirmCreate;
    case CaseType.PUT:
      return handlingConfirmUpdate;
    case CaseType.PATCH:
      return handlingConfirmUpdate;
    case CaseType.DELETE:
      return handlingConfirmDelete;
      default:
        throw new Error(`Unhandled case type: ${caseType}`);
  }
}

export function handleFormSubmit(event) {
  event.preventDefault(); // prevent the default form submission behavior

  const form = event.target; // get the form element that triggered the event
  const formData = new FormData(form); // gather form data
  const actionUrl = form.action; // get the URL from the form's action attribute

  // get the method from the form's method attribute
  // check if the form has a hidden `_method` field
  const hiddenMethodInput = form.querySelector('input[name="_method"]');
  const method = hiddenMethodInput ? hiddenMethodInput.value : form.method; // Use hidden method if available

  const confirmHandler = confirmHandlerFactory(method);
  const successHandler = successHandlerFactory(method);

  // confirm
  const confirmation = confirmHandler(formData)
  if (!confirmation) {
    return;
  }

  // send request
  submitForm(actionUrl, formData, method)
    .then(() => {
      successHandler(formData);
    })
    .catch(handleError);
}
