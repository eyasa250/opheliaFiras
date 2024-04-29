const Task = require('../model/task');
const Room = require('../model/room');
const User = require('../model/User'); 


// Créer une tâche
exports.createTask = async (req, res) => {
    const { names, roomName } = req.body;
    const userId = req.user.id;

    try {
        // Vérifier si l'utilisateur a le rôle de "mère"
        const user = await User.findById(userId);
        if (user.role !== 'mere') {
            return res.status(403).json({ message: "Only mothers are allowed to create tasks." });
        }

        // Vérifier l'existence de la chambre
        const roomExists = await Room.findOne({ nom: roomName });
        if (!roomExists) {
            return res.status(404).json({ message: "Room not found" });
        }

        // Créer les tâches avec l'admin défini comme l'utilisateur qui crée la tâche
        const tasks = [];
        for (const name of names) {
            const task = new Task({
                name,
                room: roomExists._id, // Utiliser l'ID de la salle trouvée
                admin: userId // Définir l'admin comme l'utilisateur qui crée la tâche
            });
            await task.save();
            tasks.push(task);
        }

        res.status(201).json({ message: "Tasks created successfully", tasks });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "Server Error", error: error.message });
    }
};




// Méthode pour distribuer les tâches aléatoirement entre les membres de la famille
exports.distributeTasksRandomly = async (req, res) => {
    const { nom, selectedTasks } = req.body;
    const userId = req.user.id;

    try {
        // Vérifier si l'utilisateur a le rôle de "mère"
        const user = await User.findById(userId);
        if (user.role !== 'mere') {
            return res.status(403).json({ message: "Only mothers are allowed to distribute tasks." });
        }

        // Récupérer les membres de la famille
        const family = await User.find({ familyId: user.familyId });
        const numberOfFamilyMembers = family.length;

        // Distribuer les tâches aléatoirement entre les membres de la famille
        const assignedTasks = [];
        selectedTasks.forEach(taskId => {
            const randomIndex = Math.floor(Math.random() * numberOfFamilyMembers);
            const assignedMember = family[randomIndex];
            assignedTasks.push({ task: taskId, assignedTo: assignedMember._id });
        });

        // Enregistrer les tâches attribuées
        await Task.insertMany(assignedTasks);

        res.status(200).json({ message: 'Tasks distributed successfully.' });
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
};
// Get all tasks
exports.getAllTasks = async (req, res) => {
    try {
        const tasks = await Task.find();
        res.json( tasks );
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
