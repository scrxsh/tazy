let listProductos = []
let listProveedores = []

const gallery           = document.querySelector('#gallery')
const tipoAccion        = document.querySelector("#actType")
const nombreProducto    = document.querySelector("#nombreProducto")
const urlImg            = document.querySelector("#urlImg")
const pIva              = document.querySelector("#pIva")
const precioInicial     = document.querySelector("#precioInicial")
const tipoPrenda        = document.querySelector("#tipoPrenda")
const tallaPrenda       = document.querySelector("#tallaPrenda")
const colorPrenda       = document.querySelector("#colorPrenda")
const stockPrenda       = document.querySelector("#stockPrenda")
const descripcionPrenda = document.querySelector("#descripcionPrenda")
const selectProveedor   = document.querySelector("#selectProveedor")
const idProducto        = document.querySelector("#idProducto")
const tBody             = document.querySelector("#tBody")

const BASE_URL = "http://localhost:8080/api"

// ─── Arranque: modelo ya entrenado en el backend ──────────────────────────────

obtenerProductos()
cargarProveedores()

// ─── Helpers ──────────────────────────────────────────────────────────────────

function formatCOP(value) {
    return new Intl.NumberFormat("es-CO", {
        style: "currency",
        currency: "COP",
        maximumFractionDigits: 0
    }).format(value)
}

async function fetchXSells(precioFinal) {
    try {
        const res = await fetch(`${BASE_URL}/prediccion/valor_venta?precio=${precioFinal}`)
        if (!res.ok) return "N/A"
        const data = await res.json()
        return parseFloat(data).toFixed(2)
    } catch {
        return "N/A"
    }
}

// ─── Renderizado ──────────────────────────────────────────────────────────────

function renderProductoEnDOM(element, xSellsFixed) {
    const precioFixed = formatCOP(element.precioFinal)

    gallery.insertAdjacentHTML("beforeend", `
        <clothe-card
            urlImg="${element.imgPrenda}"
            nombrePrenda="${element.nombre}"
            descripcionPrenda="${element.descripcion}"
            color="${element.color}"
            talla="${element.talla}"
            stock="${element.stock}"
            precio="${precioFixed}"
            xSells="${xSellsFixed}">
        </clothe-card>
    `)

    tBody.insertAdjacentHTML("beforeend", `
        <tr class="odd:bg-white even:bg-gray-50 border-b dark:bg-gray-800 whitespace-nowrap">
            <th scope="row" class="flex items-center px-5 py-2 text-gray-900 dark:text-gray-300">
                <img class="w-9 h-9 rounded-full object-contain" src="${element.imgPrenda}" alt="${element.nombre}">
                <div class="ps-1 whitespace-nowrap">
                    <div class="text-base font-semibold">${element.nombre}</div>
                    <div class="font-normal text-gray-500 dark:text-gray-300">
                        <strong>Talla: </strong>${element.talla} |
                        <strong>Ventas esperadas: </strong>${xSellsFixed}
                    </div>
                </div>
            </th>
            <td class="px-5 py-2 dark:text-gray-50">${precioFixed}</td>
            <td class="px-5 py-2 dark:text-gray-300">${element.stock}</td>
            <td class="px-5 py-2 dark:text-gray-300">${element.tipo}</td>
            <td class="px-5 py-2 dark:text-gray-300">${element.color}</td>
            <td class="px-5 py-2 text-sm dark:text-gray-300 max-w-xs break-words whitespace-normal">${element.descripcion}</td>
            <td class="px-5 py-2 dark:text-gray-300">${element.proveedor.nombreEmpresa}</td>
            <td class="px-5 py-2 -ml-5 flex space-x-2">
                <i class="fa-solid fa-pen-circle text-green-600 dark:text-green-400 text-3xl cursor-pointer" onclick="editarProducto('modalProduct', ${element.id})"></i>
                <i class="fa-solid fa-circle-trash text-red-600 dark:text-red-400 text-3xl cursor-pointer" onclick="confirmarEliminacion('deleteModal', ${element.id})"></i>
            </td>
        </tr>
    `)
}

// ─── CRUD ─────────────────────────────────────────────────────────────────────

async function obtenerProductos() {
    try {
        const res = await fetch(`${BASE_URL}/productos/todos`)
        const productos = await res.json()
        listProductos = productos

        const enriquecidos = await Promise.all(
            productos.map(async (element) => {
                const xSells = await fetchXSells(element.precioFinal)
                return { element, xSells }
            })
        )

        enriquecidos.forEach(({ element, xSells }) => {
            renderProductoEnDOM(element, xSells)
        })
    } catch (error) {
        console.error("Error al obtener productos:", error)
    }
}

async function crearProducto() {
    const idProveedor = selectProveedor.value
    const productoData = buildProductoData()
    const formdata = buildFormData(productoData)

    try {
        const res = await fetch(`${BASE_URL}/productos/crear?id_proveedor=${idProveedor}`, {
            method: "POST",
            body: formdata,
            redirect: "follow"
        })
        const productos = await res.json()

        const enriquecidos = await Promise.all(
            productos.map(async (element) => {
                const xSells = await fetchXSells(element.precioFinal)
                return { element, xSells }
            })
        )

        enriquecidos.forEach(({ element, xSells }) => {
            renderProductoEnDOM(element, xSells)
            listProductos.push(element)
        })

        closeModal('modalProduct')
    } catch (error) {
        console.error("Error al crear producto:", error)
    }
}

async function modificarProducto() {
    const productoData = { id: idProducto.value, ...buildProductoData() }
    const formdata = buildFormData(productoData)
    const idProveedor = selectProveedor.value

    try {
        await fetch(`${BASE_URL}/productos/actualizar?id_proveedor=${idProveedor}`, {
            method: "PUT",
            body: formdata,
            redirect: "follow"
        })
        await refrescarLista()
        closeModal('modalProduct')
    } catch (error) {
        console.error("Error al modificar producto:", error)
    }
}

async function eliminarProducto(id) {
    try {
        await fetch(`${BASE_URL}/productos/borrar/${id}`, {
            method: "DELETE",
            redirect: "follow"
        })
        await refrescarLista()
    } catch (error) {
        console.error("Error al eliminar producto:", error)
    }
}

async function refrescarLista() {
    listProductos = []
    gallery.innerHTML = ''
    tBody.innerHTML = ''
    await obtenerProductos()
}

// ─── Formulario ───────────────────────────────────────────────────────────────

function buildProductoData() {
    return {
        nombre:      nombreProducto.value.trim(),
        color:       colorPrenda.value.trim(),
        descripcion: descripcionPrenda.value,
        precio:      Number(precioInicial.value),
        piva:        Number(pIva.value),
        stock:       Number(stockPrenda.value),
        tipo:        tipoPrenda.value.trim(),
        talla:       tallaPrenda.value.trim()
    }
}

function buildFormData(productoData) {
    const producto = new Blob([JSON.stringify(productoData)], { type: 'application/json' })
    const formdata = new FormData()
    formdata.append("producto", producto)
    if (urlImg.files[0]) formdata.append("file", urlImg.files[0])
    return formdata
}

async function action() {
    switch (tipoAccion.value) {
        case "crear":
            await crearProducto()
            break
        case "editar":
            await modificarProducto()
            break
        default:
            closeModal('deleteModal')
            await eliminarProducto(idProducto.value)
            break
    }
}

function validacionFormulario() {
    const campos = [nombreProducto, pIva, precioInicial, tipoPrenda, tallaPrenda, colorPrenda, stockPrenda, descripcionPrenda]
    const hayVacio = campos.some(c => c.value === "")

    if (hayVacio) {
        alert("Por favor, completa todos los campos antes de enviar el formulario.")
        return
    }

    if (tipoAccion.value === "crear") {
        if (!urlImg.files[0]) {
            alert("Por favor, selecciona una imagen.")
            return
        }
        if (urlImg.files[0].size >= 5_242_880) {
            alert("El archivo es demasiado grande. El tamaño máximo permitido es 5MB.")
            urlImg.value = ""
            return
        }
    }

    action()
}

// ─── Modales ──────────────────────────────────────────────────────────────────

function agregarProducto(modalId) {
    openModal(modalId)
    ;[nombreProducto, urlImg, pIva, precioInicial, tipoPrenda,
      tallaPrenda, colorPrenda, stockPrenda, descripcionPrenda].forEach(el => el.value = '')
    tipoAccion.value = "crear"
}

function editarProducto(modalId, id) {
    openModal(modalId)
    const producto = listProductos.find(e => e.id === id)
    if (!producto) return

    idProducto.value        = id
    nombreProducto.value    = producto.nombre
    pIva.value              = producto.piva
    precioInicial.value     = producto.precio
    tipoPrenda.value        = producto.tipo
    tallaPrenda.value       = producto.talla
    colorPrenda.value       = producto.color
    stockPrenda.value       = producto.stock
    descripcionPrenda.value = producto.descripcion

    cargarProveedores().then(() => {
        selectProveedor.value = producto.proveedor.id
    })

    tipoAccion.value = "editar"
}

function confirmarEliminacion(modalId, id) {
    openModal(modalId)
    idProducto.value = id
    tipoAccion.value = "borrar"
}

async function cargarProveedores() {
    try {
        const res = await fetch(`${BASE_URL}/proveedores/todos`)
        listProveedores = await res.json()

        selectProveedor.innerHTML = `<option value="">Seleccione un proveedor</option>`
        listProveedores.forEach(p => {
            selectProveedor.insertAdjacentHTML("beforeend",
                `<option value="${p.id}">${p.nombreEmpresa}</option>`)
        })
    } catch (error) {
        console.error("Error al cargar proveedores:", error)
    }
}