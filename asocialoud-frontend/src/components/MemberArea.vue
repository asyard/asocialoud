<template>
    <div class="user">

        <h2>Hello, this is your member area</h2>

        here are all members :

        <button @click="listMembers()">CALL Spring Boot REST backend service</button>




        <h4>Backend response is : {{ memberList }}</h4>



    </div>
</template>

<script>
    import userapi from '../member-api';

    export default {
        name: "MemberArea",
        data () {
            return {
                memberList: '',
                errors: [],
                user: {
                    userName: '',
                    realName: '',
                    id: -1
                },
            }
        },
        methods: {
            listMembers () {
                userapi.getAll().then(response => {
                    // JSON responses are automatically parsed.
                    this.memberList = response.data.data;
                })
                    .catch(e => {
                        this.loginError = true;
                        this.errors.push(e);
                        this.memberList = e;
                    })
            }
        }
    }
</script>

<style scoped>

</style>