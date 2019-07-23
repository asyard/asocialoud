<template>
    <div class="user">
        <h1>Register</h1>

        <input type="text" v-model="user.realName" placeholder="real name" autofocus> <br/>
        <input type="text" v-model="user.userName" placeholder="user name"><br/>
        <input type="text" placeholder="email" v-model="user.userEmail"><br/>
        <input type="password" placeholder="password" v-model="user.userPass"><br/>


        <button @click="createNewUser()">Create User</button>

        <div v-if="showResponse"><h6>User created with Id: {{ user.id }}</h6></div>

        <div v-else-if="hasError"><h6>{{ errors }}</h6></div>
    </div>
</template>

<script>
    import userapi from '../member-api';

    export default {
        name: "Register",
        data () {
            return {
                response: [],
                hasError: false,
                errors: [],
                user: {
                    userName: '',
                    realName: '',
                    userEmail: '',
                    userPass: '',
                    id: -1
                },
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
            }
        },
        methods: {
            createNewUser () {
                this.errors = [];
                userapi.createNew(this.user.userName, this.user.realName, this.user.userEmail, this.user.userPass).then(response => {
                    // JSON responses are automatically parsed.
                    this.showResponse = true;
                    this.hasError = false;
                    this.response = response.data;
                    this.user.id = response.data;
                })
                    .catch(e => {
                        this.showResponse = false;
                        this.hasError = true;
                        this.errors.push(e);
                    })
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>