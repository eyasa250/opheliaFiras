const Task = require('../model/task');
const Room = require('../model/room');
const User = require('../model/User'); // Assurez-vous d'importer le modèle User

exports.createTask = async (req, res) => {
    const { name, roomId } = req.body;
    const userId = req.user.id; // Assurez-vous que l'ID de l'utilisateur est bien extrait de la session ou du token JWT

    try {
        // Vérifier si l'utilisateur a le rôle de "mère"
        const user = await User.findById(userId);
        if (user.role !== 'mere') {
            return res.status(403).json({ message: "Only mothers are allowed to create tasks." });
        }

        // Vérifier l'existence de la chambre
        const roomExists = await Room.findById(roomId);
        if (!roomExists) {
            return res.status(404).json({ message: "Room not found" });
        }

        // Créer la tâche
        const task = new Task({
            name,
            room: roomId
        });
        await task.save();
        res.status(201).json({ message: "Task created successfully", task });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Server Error", error: error.message });
    }
};

// Get all tasks
exports.getAllTasks = async (req, res) => {
    try {
        const tasks = await Task.findAll();
        res.json({ tasks });
    } catch (error) {
        console.error("Error fetching tasks:", error);
        res.status(500).json({ message: "Server error" });
    }
};

// Get task by ID
exports.getTaskById = async (req, res) => {
    const taskId = req.params.id;

    try {
        const task = await Task.findById(taskId);
        if (!task) {
            return res.status(404).json({ message: "Task not found." });
        }
        res.json({ task });
    } catch (error) {
        console.error("Error fetching task:", error);
        res.status(500).json({ message: "Server error" });
    }
};

// Update task by ID
exports.updateTaskById = async (req, res) => {
    const taskId = req.params.id;
    try {
        const { title, description, dueDate, completed } = req.body;
        const updatedTask = await Task.update(
            { title, description, dueDate, completed },
            { where: { id: taskId } }
        );
        if (updatedTask[0] === 0) {
            return res.status(404).json({ message: "Task not found." });
        }
        res.json({ message: "Task updated successfully" });
    } catch (error) {
        console.error("Error updating task:", error);
        res.status(500).json({ message: "Server error" });
    }
};

// Delete task by ID
exports.deleteTaskById = async (req, res) => {
    const taskId = req.params.id;
    try {
        const deletedTaskCount = await Task.destroy({ where: { id: taskId } });
        if (deletedTaskCount === 0) {
            return res.status(404).json({ message: "Task not found." });
        }
        res.json({ message: "Task deleted successfully" });
    } catch (error) {
        console.error("Error deleting task:", error);
        res.status(500).json({ message: "Server error" });
    }
};
exports.findTasksByRoom = async (req, res) => {
    const { idroom } = req.params; // Accessing the idroom parameter from the URL
    console.log("Room ID:");

    try {
        const tasks = await Task.find({ room: idroom }); // Use idroom to query tasks
        res.json(tasks);
    } catch (error) {
        console.log(error);
        res.status(500).json({ message: "Server Error" });
    }
};

