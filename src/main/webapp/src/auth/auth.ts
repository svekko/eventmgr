import type { AxiosInstance } from "axios";
import { ref, type Ref } from "vue";

const STORAGE_KEY_ACCESS_TOKEN = "ng_event_mgr__access_token";

interface ParsedToken {
  exp: number;
  sub: string;
  originalValue: string;
}

class Auth {
  axios: AxiosInstance;
  token: Ref<ParsedToken | null> = ref(null);

  constructor(axios: AxiosInstance) {
    this.axios = axios;
  }

  async login(username: string, password: string) {
    const resp = await this.axios.post("/auth/login", {
      username: username,
      password: password,
    });

    this.token.value = this.parseToken(resp.data.token);
    if (!this.token.value) {
      return;
    }

    localStorage.setItem(STORAGE_KEY_ACCESS_TOKEN, this.token.value.originalValue);
  }

  logout() {
    localStorage.removeItem(STORAGE_KEY_ACCESS_TOKEN);
    this.token.value = null;
  }

  getActiveToken(): ParsedToken | null {
    if (!this.token.value) {
      const token = localStorage.getItem(STORAGE_KEY_ACCESS_TOKEN);
      if (!token) {
        return null;
      }

      this.token.value = this.parseToken(token);
      if (!this.token.value) {
        return null;
      }
    }

    if ((this.token.value.exp * 1000) < new Date().getTime()) {
      return null;
    }

    return this.token.value;
  }

  isAuthenticated() {
    return this.getActiveToken() !== null;
  }

  parseToken(token: string): ParsedToken | null {
    const b64Value = token.split('.')[1]?.replace(/-/g, '+')?.replace(/_/g, '/');
    if (!b64Value) {
      return null;
    }

    const payload = decodeURIComponent(window.atob(b64Value)
      .split('')
      .map(c => {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join(''));

    if (!payload) {
      return null;
    }

    return {
      ...JSON.parse(payload),
      originalValue: token,
    };
  }
}

export default Auth;
