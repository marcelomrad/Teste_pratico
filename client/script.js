const url = 'http://localhost:8080/api/reminders';

// Função para exibir as mensagens de erro
function showError(message) {
    const errorElement = document.getElementById('error-message');
    if (errorElement) {
        errorElement.textContent = message || '';
    }
}
  

// Função para criar um novo lembrete
async function createReminder() {
  const nameInput = document.getElementById('name');
  const dateInput = document.getElementById('date');

  const name = nameInput.value;
  const date = dateInput.value;

  try {
    const response = await axios.post(`${url}/create`, { name, date });
    const reminder = response.data;

    // Limpar campos do formulário
    nameInput.value = '';
    dateInput.value = '';

    // Chamar função para atualizar a lista de lembretes
    fetchReminders();
    
  } catch (error) {
    if (error.response) {
      // O servidor retornou uma resposta com status de erro
      showError(error.response.data.message);
    } else if (error.request) {
      // A requisição foi feita, mas não houve resposta do servidor
      showError('Erro ao se comunicar com o servidor');
    } else {
      // Ocorreu algum erro ao fazer a requisição
      showError('Erro ao processar a requisição');
    }
  }
}

// Função para deletar um lembrete
async function deleteReminder(id) {
  try {
    await axios.delete(`${url}/delete/${id}`);
    // Chamar função para atualizar a lista de lembretes
    fetchReminders();
  } catch (error) {
    if (error.response) {
      // O servidor retornou uma resposta com status de erro
      showError(error.response.data.message);
    } else if (error.request) {
      // A requisição foi feita, mas não houve resposta do servidor
      showError('Erro ao se comunicar com o servidor');
    } else {
      // Ocorreu algum erro ao fazer a requisição
      showError('Erro ao processar a requisição');
    }
  }
}


// Função para exibir os lembretes na página
function showReminders(reminders) {
  const listElement = document.getElementById('reminder-list');
  if (!listElement) {
    console.error('Elemento #reminder-list não encontrado');
    return;
  }
  listElement.innerHTML = '';

  reminders.forEach((group) => {
    const listItem = document.createElement('li');
    listItem.innerHTML = `<div class="date">${formatDate(group.groupDate)}</div>`;
    group.reminders.forEach((reminder) => {
      listItem.innerHTML += `<div class="reminder">
                                - ${reminder.name}
                                <button onclick="deleteReminder(${reminder.id})">X</button>
                              </div>`;
    });
    listElement.appendChild(listItem);
  });
}





// Função para buscar e exibir a lista de lembretes
async function fetchReminders() {
  try {
    const response = await axios.get(`${url}/list`);
    const reminders = response.data;
    showReminders(reminders);
  } catch (error) {
    if (error.response) {
      // O servidor retornou uma resposta com status de erro
      showError(error.response.data.message);
    } else if (error.request) {
      // A requisição foi feita, mas não houve resposta do servidor
      showError('Erro ao se comunicar com o servidor');
    } else {
      // Ocorreu algum erro ao fazer a requisição
      showError('Erro ao processar a requisição');
    }
  }
}

// Função para formatar a data no formato DD/MM/YYYY
function formatDate(dateString) {
  const date = new Date(dateString);
  const day = date.getDate().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
}



// Função para inicializar o script após o carregamento da página
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('reminder-form');
    const errorElement = document.getElementById('error-message');

    if (form) {
        form.addEventListener('submit', (event) => {
            event.preventDefault();
            createReminder();
        });
    }

    // Remover a mensagem de erro ao digitar nos campos
    const nameInput = document.getElementById('name');
    const dateInput = document.getElementById('date');

    if (nameInput) {
        nameInput.addEventListener('input', () => {
            if (errorElement) {
                errorElement.textContent = '';
            }
        });
    }

    if (dateInput) {
        dateInput.addEventListener('input', () => {
            if (errorElement) {
                errorElement.textContent = '';
            }
        });
    }

    // Buscar e exibir a lista de lembretes
    fetchReminders();
});
