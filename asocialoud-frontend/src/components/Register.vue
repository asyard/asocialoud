<template>
    <div class="register">
        <h1>{{$t('register')}}</h1>

        <form v-if="!showResponse" @submit.prevent="createNewUser()">
            <input type="text" v-model="user.realName" :placeholder="$t('realName')" autofocus> <br/>
            <input type="text" v-model="user.userName" :placeholder="$t('userName')"><br/>
            <input type="text" :placeholder="$t('email')" v-model="user.userEmail"><br/>
            <input type="password" :placeholder="$t('password')" v-model="user.userPass"><br/>

            <b-btn variant="success" type="submit">{{$t('btn_create')}}</b-btn>
            <br/>
            {{$t('loginMsg')}} <router-link to="/login">{{$t('signIn')}}</router-link>
        </form>

        <div v-if="showResponse">
            {{$t('registerSuccess')}} <router-link to="/login">{{$t('signIn')}}</router-link>
            <br/>
            <router-view/>
        </div>

        <div v-else-if="hasError"><h6>{{$t(errorTxt)}}</h6></div>
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
                errorTxt: '',
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
                this.errorTxt = '';
                userapi.createNew(this.user.userName, this.user.realName, this.user.userEmail, this.user.userPass).then(response => {
                    if (response.data.status == 201) {
                        // JSON responses are automatically parsed.
                        this.showResponse = true;
                        this.hasError = false;
                        this.response = response.data;
                        this.user.id = response.data;
                    } else {
                        this.showResponse = false;
                        this.hasError = true;
                        this.errorTxt = response.data.data;
                    }

                })
                    .catch(e => {
                        this.showResponse = false;
                        this.hasError = true;
                        this.errorTxt = e;
                    })
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>