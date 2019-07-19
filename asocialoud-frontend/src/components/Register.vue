<template>
    <div class="user">
        <h1>Create User</h1>

        <h3>Just some database interaction...</h3>

        <input type="text" v-model="user.userName" placeholder="user name">
        <input type="text" v-model="user.realName" placeholder="real name">

        <button @click="createNewUser()">Create User</button>

        <div v-if="showResponse"><h6>User created with Id: {{ user.id }}</h6></div>

        <div v-if="hasError"><h6>{{ errors }}</h6></div>
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
                    id: -1
                },
                showResponse: false,
                retrievedUser: {},
                showRetrievedUser: false
            }
        },
        methods: {
            createNewUser () {
                userapi.createNew(this.user.userName, this.user.realName).then(response => {
                    // JSON responses are automatically parsed.
                    this.showResponse = true;
                    this.hasError = false;
                    this.response = response.data;
                    this.user.id = response.data;
                })
                    .catch(e => {
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